/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fh_dortmund.inf.cw.surstwalat.client.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Objects;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lars
 */
public class EventManager {
        
    private final HashMap<Class<? extends Event>, TreeSet<CallableEventHandler>> handler = new HashMap<>();
    
    /**
     * Registriert den Listener l im System
     * @param l Der zu registrirende Listener
     */
    public void registerListener(EventListener l) {
        Method[] meths = l.getClass().getMethods();
        for(Method m: meths) {
            if(!m.isAnnotationPresent(EventHandler.class)) {
                continue;
            }
            int modifiers = m.getModifiers();
            if(!Modifier.isPublic(modifiers)) {
                throw new EventHandlerException(m);
            }
            Class<?>[] classss = m.getParameterTypes();
            if(classss.length != 1) {
                throw new EventHandlerException(m);
            }
            Class<?> classs = classss[0];
            while(classs != null) {
                if(classs.equals(Event.class)) {
                    break;
                }
                classs = classs.getSuperclass();
            }
            if(classs == null) {
                throw new EventHandlerException(m);
            }
            registerEventHandler(new CallableEventHandler(m, l), (Class<? extends Event>) classss[0]);
        }
    }
    
    private void registerEventHandler(CallableEventHandler ceh, Class<? extends Event> eventClass) {
        if(!handler.containsKey(eventClass)) {
            handler.put(eventClass, new TreeSet<>());
        }
        handler.get(eventClass).add(ceh);
    }
    
    private void callEventHandler(Event e) {
        Class<? extends Event> classs = e.getClass();
        boolean cancellable = e instanceof Cancellable;
        
        while(true) {
            TreeSet<CallableEventHandler> list = handler.get(classs);
            if(list != null) {
                for(CallableEventHandler ceh : list) {
                    try {
                        if(cancellable) {
                            Cancellable ce = (Cancellable) e;
                            if(ce.isCancelled()) {
                                if(ceh.isIgnoreCancelled()) {
                                    ceh.invoke(e);
                                }
                            } else {
                                ceh.invoke(e);
                            }
                        } else {
                            ceh.invoke(e);
                        }
                    } catch(EventException ex) {
                        Logger.getLogger(EventManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(classs.equals(Event.class)) {
                break;
            }
            classs = (Class<? extends Event>) classs.getSuperclass();
        }
    }
    
    /**
     * Löst das Event e aus
     * @param e Das auszulösende Event
     */
    public void fireEvent(Event e) {
        callEventHandler(e);
    }
    
    private static class CallableEventHandler implements Comparable<CallableEventHandler>{
        
        private final Method m;
        private final EventListener l;
        private final EventHandler ann;

        public CallableEventHandler(Method m, EventListener l) {
            this.m = m;
            this.l = l;
            this.ann = m.getAnnotation(EventHandler.class);
        }
        
        public boolean isIgnoreCancelled() {
            return ann.ignoreCancelled();
        }
        
        private void invoke(Event e) throws EventException {
            try {
                boolean old = m.isAccessible();
                if(!old) {
                    m.setAccessible(true);
                }
                if(Modifier.isStatic(m.getModifiers())) {
                    m.invoke(null, e);
                } else {
                    m.invoke(l, e);
                }
                if(!old) {
                    m.setAccessible(old);
                }
            } catch(InvocationTargetException ex) {
                throw new EventException(ex.getCause());
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException ex) {
                throw new EventException(ex);
            } 
        }

        @Override
        public int compareTo(CallableEventHandler o) {
            int result = ann.priority().getRank() - o.ann.priority().getRank();
            if(result == 0) {
                result = m.toGenericString().compareTo(o.m.toGenericString());
            }
            return result;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 83 * hash + Objects.hashCode(this.m);
            hash = 83 * hash + Objects.hashCode(this.l);
            hash = 83 * hash + Objects.hashCode(this.ann);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final CallableEventHandler other = (CallableEventHandler) obj;
            if (!Objects.equals(this.m, other.m)) {
                return false;
            }
            return Objects.equals(this.ann, other.ann);
        }
   
    }

}

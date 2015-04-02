package cl.thread;


/*
 ** JSO1.0, by Allen Huang,2007-6-19
 */
public interface IThreadReceiver {

    public void add(Object page);
    
    public void setDoDeal(boolean doDeal);
    
    public void validate();
}


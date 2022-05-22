import java.util.Iterator;

public class MyMassiveIterator implements MassiveIterator {
    private MyMassiveTreeNode node;
    private Massive nextElement;
    private char status = 'l';

    private MassiveIterator iterator;

    public MyMassiveIterator(MyMassiveTreeNode node){
        this.node = node;

        nextElement = node.getKey();
    }

    private Massive retrieveNextElement(){
        if (iterator !=null){
            if(iterator.hasNext()){
                return iterator.next();
            }
            iterator = null;
        }

        if(status == 'l'){
            //set next status
            status = 'r';
            if(node.getLeft() != null){
                //use iterator of left node
                iterator = node.getLeft().keyIterator();
            }
        }

        if(iterator == null && status == 'r'){
            //set next (non reachable status)
            status = 'x';
            if(node.getRight() != null){
                iterator = node.getRight().keyIterator();
            }
        }

        if(iterator != null){
            return (Massive) iterator.next();
        }
        return null;


    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Massive next() {
        Massive tempNext = nextElement;

        //get nextElement
        nextElement = retrieveNextElement();

        return tempNext;
    }
}

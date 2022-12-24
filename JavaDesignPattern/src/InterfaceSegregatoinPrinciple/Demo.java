package InterfaceSegregatoinPrinciple;

class Document {

}
// Too manu stuff in this interface. Not all machines would be using these many methods
interface Machine {
    void print(Document d);
    void fax(Document d);
    void scan(Document d);
}

class MultiuFunctionPrinter implements Machine {

    @Override
    public void print(Document d) {

    }

    @Override
    public void fax(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
    
}

// This one does not support scan and fax but we still need to implement these, event exception would need to be propagated
class OldFashionedPrinter implements Machine {

    @Override
    public void print(Document d) {
        
    }

    @Override
    public void fax(Document d) {
        
    }

    @Override
    public void scan(Document d) {
        
    }
}

interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

interface Fax {
    void fax(Document d);
}

// YAGNI = You Aint gonna need it
class JustAPrinter implements Printer {

    @Override
    public void print(Document d) {
        
    }
}

class PhotoCopier implements Printer, Scanner {

    @Override
    public void scan(Document d) {
        
    }

    @Override
    public void print(Document d) {
        
    }
    
}

interface MultiFunctionalDevice extends Printer, Scanner {

}

class MultiFunctionalMachine implements MultiFunctionalDevice {

    private Printer printer;
    private Scanner scanner;
    
    public MultiFunctionalMachine(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document d) {
        printer.print(d);
    }

    @Override
    public void scan(Document d) {
        scanner.scan(d);
    }

}
public class Demo {
}

package Ex1;

public class ComplexFunction implements complex_function {
    private Operation op;
    private function right;
    private function left;

    @Override
    public void plus(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Plus;
        this.right = f1.copy();
    }

    @Override
    public void mul(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Times;
        this.right = f1.copy();
    }

    @Override
    public void div(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Divid;
        this.right = f1.copy();
    }

    @Override
    public void max(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Max;
        this.right = f1.copy();
    }

    @Override
    public void min(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Min;
        this.right = f1.copy();
    }

    @Override
    public void comp(function f1) {
        function f = new ComplexFunction(getOp().toString(), left(), right());
        this.left = f.copy();

        this.op = Operation.Comp;
        this.right = f1.copy();
    }

    @Override
    public function left() {
        return this.left;

    }

    @Override
    public function right() {
        return this.right;

    }

    @Override
    public Operation getOp() {
        return this.op;

    }

    @Override
    public double f(double x) {
        switch (getOp()) {
            case Plus:
                return left.f(x) + right.f(x);
            case Times:
                return left.f(x) * right.f(x);
            case Divid:
                Double temp=right.copy().f(x);
                if(temp==0){
                    throw new RuntimeException("Cant Divid By zero!");
                }
                return left.f(x) / right.f(x);
            case Max:
                return Math.max(left.f(x), right.f(x));
            case Min:
                return Math.min(left.f(x), right.f(x));
            case Comp:
                return left.f(right.f(x));
            case None:
                return left.f(x);
            case Error:
                throw new RuntimeException("Invalid Operation");
        }
        return 0.0;
    }

    public boolean equals(Object o) {
        boolean b = true;
        if (o instanceof function) {
            function fun = (function) o;
            for (double i = -20; i < 20; i = i + 0.5) {
                if (Math.abs(this.f(i) - fun.f(i)) > 0.00001) {
                    b = false;
                }
            }
        } else {
            b = false;
        }
        return b;
    }

    @Override
    public function copy() {
        function fun = new ComplexFunction(this);
        return fun;
    }

    public ComplexFunction(function f1) {
        if (f1 instanceof ComplexFunction) {
            ComplexFunction co = (ComplexFunction) f1;
            right = co.right().copy();
            left = co.left().copy();
            op = co.getOp();
        } else {
            right = null;
            left = f1.copy();
            op = Operation.None;
        }
    }

    public ComplexFunction(String s, function left, function right) {
        this.right = right.copy();
        this.left = left.copy();
        this.op = Operation_String(s);
    }

    public ComplexFunction() {
        ComplexFunction co = null;
    }

    private Operation Operation_String(String s) {
        String st=s.toLowerCase();
        switch (st) {
            case "plus":
                return Operation.Plus;
            case "mul":
                return Operation.Times;
            case "times":
                return Operation.Times;
            case "div":
                return Operation.Divid;
            case "divid":
                return Operation.Divid;
            case "max":
                return Operation.Max;
            case "min":
                return Operation.Min;
            case "comp":
                return Operation.Comp;
            case "none":
                return Operation.None;
            case "error":
                return Operation.Error;
            default:
                throw new RuntimeException("Invalid Operation");
        }
    }

    private String String_Operation(Operation o) {
        switch (o) {
            case Times:
                return "Times";
            case Error:
                return "Error";
            case Comp:
                return "Comp";
            case Min:
                return "Min";
            case Max:
                return "Max";
            case Divid:
                return "Div";
            case Plus:
                return "Plus";
            default:
                throw new RuntimeException("Invalid Operation");
        }
    }

    @Override
    public String toString() {
        if (getOp() == Operation.None) {
            return left().toString();
        }
        return String_Operation(getOp()) + "(" + left() + ", " + right() + ")";
    }
    @Override
    public function initFromString(String s) {
        ComplexFunction c=null;
        try {
            if (s.lastIndexOf(')')==s.length()-1) {
                String st=s.substring(0,s.indexOf('('));
                Operation o=Operation_String(st);
                int BracketCounter = 0;
                int j = 0;
                boolean flag = true;
                for (int k = s.indexOf('(')+1; k < s.length() - 1 && flag; k++) {
                    if(s.charAt(k)=='('){
                        BracketCounter--;
                    }
                    if(s.charAt(k)==')'){
                        BracketCounter++;
                    }
                    if (BracketCounter == 0 && s.charAt(k) == ',') {
                        flag = false;
                        j = k;
                    }
                }
                int temp=s.indexOf('(')+1;
                function l=initFromString(s.substring(temp,j));
                int temp2=s.length()-1;
                function r=initFromString(s.substring(j+1,temp2));
                c=new ComplexFunction(o.toString(),l,r);
            }
            else{
                String str=s.substring(s.indexOf('(')+1,s.length());
                return new Polynom(str);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Invalid String, function pattern not found/Operation not found/Invalid Polynom writen");
        }
        return c;
    }
}

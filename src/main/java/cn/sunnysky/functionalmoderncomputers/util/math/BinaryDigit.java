package cn.sunnysky.functionalmoderncomputers.util.math;

public class BinaryDigit {
    public int value;
    public BinaryDigit(int v) {
        if ( v == 0 || v == 1)
            value = v;
        else
            throw new RuntimeException("Cannot construct binary digit for a non-binary integer");
    }

}

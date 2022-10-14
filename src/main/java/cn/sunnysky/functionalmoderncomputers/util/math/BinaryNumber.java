package cn.sunnysky.functionalmoderncomputers.util.math;

import java.util.ArrayList;
import java.util.List;

public class BinaryNumber extends Number{
    private volatile List<BinaryDigit> digitList;

    public BinaryNumber(int dexValue){
        digitList = new ArrayList<>();
        final String binaryString = Integer.toBinaryString(dexValue);
        for (char c : binaryString.toCharArray())
            if ( c == '0')
                digitList.add(new BinaryDigit(0));
            else if ( c == '1')
                digitList.add(new BinaryDigit(1));
    }

    @Override
    public int intValue() {
        int a = 0;
        int c = 0;
        for ( BinaryDigit b: digitList){
            a += b.value * Math.pow(2,c);
            c++;
        }

        return a;
    }

    @Override
    public long longValue() {
        return intValue();
    }

    @Override
    public float floatValue() {
        return 0;
    }

    @Override
    public double doubleValue() {
        return 0;
    }
}

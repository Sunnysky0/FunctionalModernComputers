package cn.sunnysky.functionalmoderncomputers.api.computer;

import cn.sunnysky.functionalmoderncomputers.util.math.BinaryNumber;

public abstract class IBasicComputerComponent {

    IBasicComputerComponent next;

    public void onInvoke(BinaryNumber data){
        next.onInvoke(unitProcess(data));
    }

    public void setNext(IBasicComputerComponent component){
        next = component;
    }

    abstract BinaryNumber unitProcess(BinaryNumber input);
}

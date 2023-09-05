package org.example.light;

public class Attenuation {
    float constant;
    float linear;
    float exponential;

    public Attenuation(float constant, float linear, float exponential) {
        this.constant = constant;
        this.linear = linear;
        this.exponential = exponential;
    }

    public float getConstant() {
        return constant;
    }

    public float getLinear() {
        return linear;
    }

    public float getExponential() {
        return exponential;
    }
}

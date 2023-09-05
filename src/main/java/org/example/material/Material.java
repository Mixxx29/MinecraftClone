package org.example.material;

import org.joml.Vector4f;

public class Material {
    private Vector4f ambient;
    private Vector4f diffuse;
    private Vector4f specular;
    private boolean hasTexture;
    private float reflectance;

    private boolean isLit;

    public Material() {
        this.reflectance = 0.0f;
        this.ambient = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.diffuse = ambient;
        this.specular = ambient;
        this.isLit = true;
    }

    public Material(Vector4f ambient, float reflectance) {
        this.reflectance = reflectance;
        this.ambient = ambient;
        this.diffuse = ambient;
        this.specular = ambient;
        this.isLit = true;
    }

    public Material(
            Vector4f ambient,
            Vector4f diffuse,
            Vector4f specular,
            boolean hasTexture,
            float reflectance,
            boolean isLit
    ) {
        this.reflectance = reflectance;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.hasTexture = hasTexture;
        this.isLit = isLit;
    }

    public float getReflectance() {
        return reflectance;
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public boolean hasTexture() {
        return hasTexture;
    }

    public void setHasTexture(boolean hasTexture) {
        this.hasTexture = hasTexture;
    }

    public boolean isLit() {
        return isLit;
    }

    public void setLit(boolean lit) {
        isLit = lit;
    }
}

package org.example.shader;

import org.example.light.PointLight;
import org.example.material.Material;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private final int programId;
    private final Map<String, Integer> uniforms;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram() {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create shader program");
        }
        uniforms = new HashMap<>();
    }

    public void createPointLightUniform(String uniformName) {
        createUniform(uniformName + ".color");
        createUniform(uniformName + ".position");
        createUniform(uniformName + ".intensity");
        createUniform(uniformName + ".attenuation.constant");
        createUniform(uniformName + ".attenuation.linear");
        createUniform(uniformName + ".attenuation.exponential");
    }

    public void createMaterialUniform(String uniformName) {
        createUniform(uniformName + ".ambient");
        createUniform(uniformName + ".diffuse");
        createUniform(uniformName + ".specular");
        createUniform(uniformName + ".hasTexture");
        createUniform(uniformName + ".reflectance");
        createUniform(uniformName + ".isLit");
    }

    public void createUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new RuntimeException("Could not create uniform: " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, PointLight pointLight, Matrix4f viewMatrix) {
        setUniform(uniformName + ".color", pointLight.getColor());
        setUniform(uniformName + ".position", pointLight.getCameraViewPosition(viewMatrix));
        setUniform(uniformName + ".intensity", pointLight.getIntensity());
        setUniform(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
        setUniform(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
        setUniform(uniformName + ".attenuation.exponential", pointLight.getAttenuation().getExponential());
    }

    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ambient", material.getAmbient());
        setUniform(uniformName + ".diffuse", material.getDiffuse());
        setUniform(uniformName + ".specular", material.getSpecular());
        setUniform(uniformName + ".hasTexture", material.hasTexture() ? 1 : 0);
        setUniform(uniformName + ".reflectance", material.getReflectance());
        setUniform(uniformName + ".isLit", material.isLit() ? 1 : 0);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer floatBuffer = stack.mallocFloat(16);
            value.get(floatBuffer);
            glUniformMatrix4fv(uniforms.get(uniformName), false, floatBuffer);
        }
    }

    public void setUniform(String uniformName, Vector4f value) {
        glUniform4f(
                uniforms.get(uniformName),
                value.x,
                value.y,
                value.z,
                value.w
        );
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(
                uniforms.get(uniformName),
                value.x,
                value.y,
                value.z
        );
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void createVertexShader(String shaderCode) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderCode, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new RuntimeException("Could not create shader of type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new RuntimeException(
                    "Error compiling shader code: " + glGetShaderInfoLog(shaderId, 1024)
            );
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    public void link() {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new RuntimeException(
                    "Error linking shader code: " + glGetProgramInfoLog(programId, 1024)
            );
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }

        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw new RuntimeException(
                    "Warning validating shader code: " + glGetProgramInfoLog(programId, 1024)
            );
        }
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}

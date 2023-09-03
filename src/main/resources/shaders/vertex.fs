#version 450

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoords;

out vec2 outTextureCoords;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main()
{
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
    outTextureCoords = textureCoords;
}
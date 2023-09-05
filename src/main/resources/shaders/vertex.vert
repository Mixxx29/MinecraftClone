#version 450

layout (location=0) in vec3 position;
layout (location=1) in vec2 textureCoords;
layout (location=2) in vec3 vertexNormal;

out vec3 outPosition;
out vec2 outTextureCoords;
out vec3 outVertexNormal;

uniform mat4 projectionMatrix;
uniform mat4 modelViewMatrix;

void main()
{
    vec4 worldPosition = modelViewMatrix * vec4(position, 1.0);
    gl_Position = projectionMatrix * worldPosition;

    outPosition = worldPosition.xyz;
    outTextureCoords = textureCoords;
    outVertexNormal = normalize(modelViewMatrix * vec4(vertexNormal, 0)).xyz;
}
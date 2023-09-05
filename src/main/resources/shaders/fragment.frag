#version 450

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    vec3 color;
    vec3 position;
    float intensity;
    Attenuation attenuation;
};

struct Material
{
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

in vec2 outTextureCoords;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform int useColor;

void main()
{
    if (useColor == 1)
    {
        fragColor = vec4(color, 1);
    }
    else
    {
        fragColor = texture(textureSampler, outTextureCoords);
    }
}
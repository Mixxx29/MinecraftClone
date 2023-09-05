#version 450

struct Attenuation
{
    float constant;
    float linear;
    float exponential;
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
    int isLit;
};

in vec3 outPosition;
in vec2 outTextureCoords;
in vec3 outVertexNormal;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 ambientColor;
uniform float specularPower;
uniform PointLight pointLight;
uniform Material material;

vec4 ambientComponent;
vec4 diffuseComponent;
vec4 specularComponent;

vec4 calculatePointLight(PointLight pointLight, vec3 position, vec3 normal)
{
    // Diffuse light
    vec3 toSourceVector = pointLight.position - position;
    vec3 toSourceDirection = normalize(toSourceVector);
    float diffuseFactor = max(dot(normal, toSourceDirection), 0.0);
    vec4 diffuseColor = diffuseComponent * diffuseFactor * vec4(pointLight.color, 1.0) * pointLight.intensity;

    // Specular light
    vec3 cameraDirection = normalize(-position);
    vec3 fromSourceDirection = -toSourceDirection;
    vec3 reflectedLightDirection = normalize(reflect(fromSourceDirection, normal));
    float specularFactor = max(dot(cameraDirection, reflectedLightDirection), 0);
    specularFactor = pow(specularFactor, specularPower);
    vec4 specularColor = specularComponent * specularFactor * material.reflectance * vec4(pointLight.color, 1.0);

    // Attenuation
    float distance = length(toSourceVector);
    float attenuationInverted = pointLight.attenuation.constant;
    attenuationInverted += pointLight.attenuation.linear * distance;
    attenuationInverted += pointLight.attenuation.exponential * distance * distance;

    return (diffuseColor + specularColor) / attenuationInverted;
}

void setupColors(Material material, vec2 textureCoords)
{
    if (material.hasTexture == 1)
    {
        ambientComponent = texture(textureSampler, textureCoords);
        diffuseComponent = ambientComponent;
        specularComponent = ambientComponent;
    }
    else
    {
        ambientComponent = material.ambient;
        diffuseComponent = material.diffuse;
        specularComponent = material.specular;
    }
}

void main()
{
    setupColors(material, outTextureCoords);
    vec4 diffuseSpecularComponent = calculatePointLight(pointLight, outPosition, outVertexNormal);
    if (material.isLit == 1) {
        fragColor = ambientComponent * vec4(ambientColor, 1.0) * 0.5 + diffuseSpecularComponent * 0.5;
    }
    else
    {
        fragColor = ambientComponent;
    }
}
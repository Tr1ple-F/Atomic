#version 400 core

in vec2 passTC;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(lightVector);

    float nDotl = dot(unitNormal, unitLight);
    float brightness = max(nDotl, 0.1);
    vec3 diffuse = brightness * lightColor;

    vec3 unitCamera = normalize(cameraVector);
    vec3 lightDir = -unitLight;
    vec3 reflectLight = reflect(lightDir, unitNormal);

    float specularFactor = dot(reflectLight, unitCamera);
    specularFactor = max(specularFactor, 0.0);
    float dampedFactor = pow(specularFactor, shineDamper);
    vec3 finalSpecular = dampedFactor * lightColor;

    out_color = vec4(diffuse, 1.0) * texture(textureSampler, passTC) + vec4(finalSpecular, 1.0);

}

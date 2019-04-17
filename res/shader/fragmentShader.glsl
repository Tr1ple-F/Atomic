#version 400 core

in vec2 passTC;
in vec3 surfaceNormal;
in vec3 lightVector;

out vec4 out_color;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main() {

    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(lightVector);

    float nDotl = dot(unitNormal, unitLight);
    float brightness = max(nDotl, 0.1);
    vec3 diffuse = brightness * lightColor;

    out_color = vec4(diffuse, 1.0) * texture(textureSampler, passTC);

}

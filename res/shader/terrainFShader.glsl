#version 400 core

in vec2 passTC;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;
in float visibility;

out vec4 out_color;

uniform sampler2D blendMap;
uniform sampler2D rMap;
uniform sampler2D gMap;
uniform sampler2D bMap;
uniform sampler2D baseMap;

uniform vec3 lightColor;
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {

    vec4 blendMapColor = texture(blendMap, passTC);
    float baseMAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
    vec2 tiledCoords = passTC * 40;
    vec4 baseTColor = texture(baseMap, tiledCoords) * baseMAmount;
    vec4 rTColor = texture(rMap, tiledCoords) * blendMapColor.r;
    vec4 gTColor = texture(gMap, tiledCoords) * blendMapColor.g;
    vec4 bTColor = texture(bMap, tiledCoords) * blendMapColor.b;

    vec4 totalColor = baseTColor + rTColor + gTColor + bTColor;

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

    out_color = vec4(diffuse, 1.0) * totalColor + vec4(finalSpecular, 1.0);
    out_color = mix(vec4(skyColor, 1.0), out_color, visibility);

}

#version 400 core

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 passTC;
out vec3 surfaceNormal;
out vec3 lightVector;
out vec3 cameraVector;
out float visibility;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

const float density = 0.01;
const float gradient = 1.5;

void main(void) {

    vec4 world_position = transformationMatrix * vec4(position, 1.0);
    vec4 positionToCam = viewMatrix * world_position;
    gl_Position = projectionMatrix * positionToCam;
    passTC = textureCoord;

    surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
    lightVector = lightPosition - world_position.xyz;
    cameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - world_position.xyz;

    float distance = length(positionToCam.xzy);
    visibility = exp(-pow((distance*density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);

}

#version 400 core

in vec3 position;

out vec3 color;

void main(void) {

    gl_Position = vec4(position.x, position.y, position.z, 1.0);
    color = vec3(position.xyz+0.5);

}

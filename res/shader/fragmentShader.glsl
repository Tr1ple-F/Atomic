#version 400 core

in vec2 passTC;

out vec4 out_color;

uniform sampler2D textureSampler;

void main() {

    out_color = texture(textureSampler, passTC);

}

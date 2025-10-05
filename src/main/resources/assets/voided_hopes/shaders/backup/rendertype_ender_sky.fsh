#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;

out vec4 fragColor;

void main() {
    float scale = 10;

    vec4 scaledTexProj = texProj0;
    scaledTexProj.xy *= scale;

    fragColor = vec4(textureProj(Sampler0, scaledTexProj).rgb, 0);
}
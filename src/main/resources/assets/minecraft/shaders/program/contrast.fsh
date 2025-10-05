#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;
uniform float Time;

out vec4 fragColor;

void main() {
	vec3 col = texture(DiffuseSampler, texCoord).rgb;

	col = (0.3 - col) * 6.5;

	fragColor = vec4(col, 1.0);
}





































































































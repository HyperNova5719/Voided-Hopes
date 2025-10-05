#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;
uniform float Time;
uniform float Force;

out vec4 fragColor;

void main() {
	vec2 texel = vec2(0.01, 0.01) * 3. * Force;

	vec3 col = texture(DiffuseSampler, texCoord).rgb;
	vec3 col2 = texture(DiffuseSampler, texCoord + (texel * (sin(20.0 * col.rb)))).rgb;

	//col += abs(col - col2) * 4.0;

	col2 = col2 * vec3(1.6,1.7,2.2) * col2;


	float weight = 2.;

	weight += length(col2);

	weight *= Force;

	fragColor = vec4((col + (col2 * weight)) / (weight + 1), 1.0);
}





































































































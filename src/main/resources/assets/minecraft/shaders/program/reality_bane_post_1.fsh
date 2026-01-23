#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;
uniform float TT;
uniform float Corruption;
uniform float Force;

out vec4 fragColor;

float hash(vec2 p) {
	return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453123);
}

float smoothHash(vec2 p) {
	vec2 i = floor(p);
	vec2 f = fract(p);
	vec2 u = f * f * (3.0 - 2.0 * f);
	float a = hash(i);
	float b = hash(i + vec2(1.0, 0.0));
	float c = hash(i + vec2(0.0, 1.0));
	float d = hash(i + vec2(1.0, 1.0));
	return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

vec3 smoothHash3D(vec3 p) {
	vec3 i = floor(p);
	vec3 f = fract(p);
	vec3 u = f * f * (3.0 - 2.0 * f);
	vec2 xy = i.xy;
	float z0 = i.z;
	float z1 = z0 + 1.0;

	vec2 o1 = vec2(5.2, 1.3);
	vec2 o2 = vec2(8.3, 2.8);
	vec2 o3 = vec2(2.7, 9.1);

	#define NOISE_CH(o, ch) \
        float a##ch = hash(xy + vec2(0.0, 0.0) + z0 * o); \
        float b##ch = hash(xy + vec2(1.0, 0.0) + z0 * o); \
        float c##ch = hash(xy + vec2(0.0, 1.0) + z0 * o); \
        float d##ch = hash(xy + vec2(1.0, 1.0) + z0 * o); \
        float e##ch = hash(xy + vec2(0.0, 0.0) + z1 * o); \
        float f##ch = hash(xy + vec2(1.0, 0.0) + z1 * o); \
        float g##ch = hash(xy + vec2(0.0, 1.0) + z1 * o); \
        float h##ch = hash(xy + vec2(1.0, 1.0) + z1 * o); \
        float ch = mix(mix(mix(a##ch, b##ch, u.x), mix(c##ch, d##ch, u.x), u.y), \
                      mix(mix(e##ch, f##ch, u.x), mix(g##ch, h##ch, u.x), u.y), u.z);

	NOISE_CH(o1, r)
NOISE_CH(o2, g)
NOISE_CH(o3, b)

return vec3(r, g, b);
}



void main() {
    float co = Corruption;

	float time = TT * 1000.0;
	vec2 texel = vec2(1.0) / InSize;

	float eventHash = floor((90.0 - (70 * co)) * hash(vec2(floor(time * 0.2), 0.0)));



	float tHash0 = hash(vec2(floor(time * 0.2), 0.0));
	float tHash = pow(tHash0, 5.0);
	float tHash2 = pow(tHash, 5.0);
	float pHash = hash(texCoord) - 0.5;
	vec3 colHash = vec3(0.5 + fract(pHash * 100.0),0.5 +  fract(pHash * 2350.0),0.5 +  fract(pHash * 8210.0));


	float yHash = hash(vec2(floor(texCoord.y * 50.0), floor((0.05 + (2.0 * tHash)) * time))) - 0.5;
	//yHash *= Corruption;
	float bigLineFail = step(1.1 - (1.0 * tHash), abs(yHash));

    vec2 tc = texCoord;

	tc.y = (eventHash == 0.0 && co > 0.6)? 1.0 - tc.y: tc.y;
	tc.x = (eventHash == 1.0 && co > 0.4)? 1.0 - tc.x: tc.x;

	tc.x += step(0.1, co) * bigLineFail * yHash * texel.x * 100.0 * (0.2 + tHash2);

    float bordering = max(abs(tc.x - 0.5), abs(tc.y - 0.5)) * 2.0;
	float sBordering = bordering;
	bordering = (floor(bordering * 5.0 + (yHash * 4.0)) - (3.5 * yHash)) / 5.0;
	bordering *= bordering;

	bigLineFail = step(3.1 - (1.0 * tHash) - (bordering * 0.2) - (3.0 * pow(co, 0.5)), abs(yHash));

	float c = 5.0;
	float c2 = 15.0;

	float id = floor(tc.x * c) + (floor(tc.y * c2) * c);

	float dis = bigLineFail * 300.0;

	float dis2 = hash(vec2(id, 1.0)) - 0.5;

	vec3 col = texture(DiffuseSampler, tc).rgb;
	vec3 colL = texture(DiffuseSampler, tc + (texel * vec2(dis, 0.0))).rgb;
	vec3 colR = texture(DiffuseSampler, tc - (texel * vec2(dis, 0.0))).rgb;

	vec3 abCol = vec3(colR.r, col.g, colL.b);

	vec3 color = abCol + abs(0.1 * bigLineFail);

	color = color - ((color - 1.0 + color) * step(abs(id - (tHash0 * c * c2)), co * co * co * co * 1.5));

    color *= 1.0 + (co * bordering * 7.0 * colHash);

    color = (eventHash == 2.0 && co > 0.1)? color * color: color;

    color = (eventHash == 3.0 && co > 0.3)? color * normalize(color.bgr): color;
    color = (eventHash == 4.0 && co > 0.4)? normalize(color.bgr): color;





	fragColor = vec4(color, 1.0);
}





































































































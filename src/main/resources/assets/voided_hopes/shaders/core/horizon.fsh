#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform vec4 ColorModulator;
uniform int EndPortalLayers;
uniform float GameTime;

in vec4 texProj0;
in vec4 vertexColor;
in vec2 texCoord0;
in vec3 Position;

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
    float dot = vertexColor.r * 2.0 - 1.0;

    float v = 0.4 / (dot);
    v -= 0.8;


    vec3 col = vec3(v) * vec3(4.0, 2.0, 1.0);
    fragColor = vec4(clamp(col, vec3(0.0), vec3(1.0)), 1.0);
}

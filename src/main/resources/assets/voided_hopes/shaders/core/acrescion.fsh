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


const vec3[] COLORS = vec3[](
vec3(0.0, 0.0, 0.0),
vec3(0.02, 0.5, 0.05),
vec3(0.02, 0.5, 0.05),
vec3(0.02, 0.5, 0.05),
vec3(0.02, 0.5, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, 1.0, 0.05),
vec3(0.02, -1.0, 0.0),
vec3(0.02, -1.0, 0.0),
vec3(0.02, -1.0, 0.0),
vec3(0.02, -1.0, 0.0)
);


const vec3[] WHITE_COLORS = vec3[](
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0),
vec3(1.0, 1.0, 1.0)
);

const mat4 SCALE_TRANSLATE = mat4(
0.5, 0.0, 0.0, 0.25,
0.0, 0.5, 0.0, 0.25,
0.0, 0.0, 1.0, 0.0,
0.0, 0.0, 0.0, 1.0
);

mat4 end_portal_layer(float layer) {
    layer *= 8;

    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((4.5 - layer / 4.0) * 2.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

vec4 voidShader() {
    vec3 color = WHITE_COLORS[0];
    for (int i = 0; i < EndPortalLayers; i++) {
        vec3 starColor = textureProj(Sampler1,0.4 * texProj0 * end_portal_layer(float(i + 1))).rgb;
        color -= starColor * WHITE_COLORS[i + 1];
    }
    return vec4(1.0 - min(vec3(1.0), color) , 1.0);
}

vec4 voidShader2() {
    vec3 color = WHITE_COLORS[0];
    for (int i = 0; i < EndPortalLayers; i++) {
        vec3 starColor = textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb;
        color -= starColor * WHITE_COLORS[i + 1];
    }
    return vec4(color, 1.0);
}

void main() {

        float iTime = GameTime * 100.0;

        vec2 uv = fract(texCoord0 + vec2(0.0,iTime + 7.0 * texCoord0.x));

        float v = 0.2 / abs(sin((uv.x * 60.0) + (uv.y * 3.1415 * 3.0)));
        vec4 color = vec4(.0);

        float a = pow(1.0 - uv.x, 0.6);

        vec3 col = 0.4 * vec3(3.0, 2.0, 1.0) * smoothHash3D(vec3(100.0 * uv * vec2(1.0, 0.1) + vec2(iTime, 20.0 * iTime), iTime));

        color.a = a * length(col) * 2.5;

        color.rgb = col + (a * a);

        fragColor = color;
}

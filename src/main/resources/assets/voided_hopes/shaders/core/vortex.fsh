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

float smoothHash3(vec3 p) {
    float v = sin(4.0 * sin((p.z * 3.14159265359 * 5.0) + (p.x + (p.y * 93.0))));

    v += sin(30.0*sin(30.0*sin(p.x * 10.0 + (p.y * 20.0))));
    v += sin(30.0*sin(30.0*sin(p.y * 30.0)));
    return v;
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

    vec2 uv = texCoord0;

    float twist = 20.0;
    float speed = 2.4;

    float n = uv.y;

    n *= 0.4 + 0.4 * sin(uv.x * 3.14159265359 * 13.0 + (speed * iTime) + (twist * uv.y));

    float h = smoothHash3(vec3(iTime * 0.1, uv.yx));
    float h2 = smoothHash3(vec3(iTime * 0.03, uv.yx * vec2(0.02,1.0)));

    n /= abs(h2 * h) + 0.3;

    float phase = (1.0 * uv.y * uv.y) + (uv.y * -13.0) + (20 * iTime);

    n += 0.3;

    n *= 0.05 / abs(fract(16.0 * uv.x + phase) - 0.5);

    n = ((n - 0.3) * abs(n - 0.3) * 12.0) + 0.3;

    n += uv.y;

    vec4 color = vec4(voidShader().rgb, smoothstep(0.2, 0.35, n));

    fragColor = color + vec4(0.0, 0.5 * (1.0 - uv.y), 0.0, 0.0);

    fragColor.a -= pow(pow(uv.y + 0.05, 25.0) * abs(sin((iTime * 10.0) + uv.x * 10.0 * 3.14159265359)) + 0.3, 2.0);

    fragColor.a *= vertexColor.a;
}

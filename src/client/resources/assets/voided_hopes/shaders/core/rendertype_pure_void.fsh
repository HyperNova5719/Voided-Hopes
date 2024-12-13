#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
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

out vec4 fragColor;

void main() {
vec3 color = COLORS[0]; // Start with the white background
for (int i = 0; i < EndPortalLayers; i++) {
vec3 starColor = textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb;
color -= starColor * COLORS[i + 1]; // Subtract star layers for darker spots
}
fragColor = vec4(color, 1.0);
}
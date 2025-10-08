#version 150

#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in ivec2 UV2;

uniform sampler2D Sampler2;

uniform vec3 epicenter;
uniform vec2 state;
    

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform vec4 ColorModulator;
uniform int FogShape;

out float vertexDistance;
flat out vec4 vertexColor;

void main() {

    vec3 p = (vec4(Position, 1.0)).xyz;
    float dist = distance(p, epicenter);
    float t = state.x;
    float rad = t * 300.0;
    float cameraCorrectionPhase = (length(epicenter) - rad) * 0.3;
    float centerDis = sin(2.0 * cameraCorrectionPhase) / (cameraCorrectionPhase * max(1.0, cameraCorrectionPhase));
    float phase = (dist - rad) * 0.3;
    float displacement = (sin(2.0 * phase) / (phase * max(1.0, phase))) - centerDis;
    vec3 disPos = p;
    disPos.y += displacement * 10.0 * (1.0-t) * state.y;
    
    gl_Position = ProjMat * ModelViewMat * vec4(disPos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vertexColor = Color * ColorModulator * texelFetch(Sampler2, UV2 / 16, 0);
}
#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler1;

uniform vec3 epicenter;
uniform vec2 state;
uniform vec2 corruptionState;
    
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform int FogShape;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec4 overlayColor;
out vec2 texCoord0;
out vec4 normal;

void main() {

    vec3 p = (vec4(Position, 1.0)).xyz;
    float dist = distance(p, epicenter);
    float t = abs(state.x - 1.0);
    float pMultiplier = 2.0 - sign(state.x);
    float rad = t * 400.0;
    float cameraCorrectionPhase = (length(epicenter) - rad) * 0.3 * pMultiplier;
    float centerDis = sin(1.0 * cameraCorrectionPhase) / (cameraCorrectionPhase * max(1.0, cameraCorrectionPhase));
    float phase = (dist - rad) * 0.3 * pMultiplier;
    float displacement = (sin(1.0 * phase) / (phase * max(1.0, phase))) - centerDis;
    vec3 disPos = p;
    disPos += sin(p * 100.0) * corruptionState.x;
    disPos.y += (displacement * 60.0 * (1.0 - t) * state.y) * (1.0 - smoothstep(2.0, 4.0, state.x));


    float s = 20.0;
    float s2 = 10.0;
    vec3 bbPos = epicenter + vec3(0.0, 25.0 * s2, 0.0);
    
    if (state.y != 0.0) {
        vec3 rayDir = normalize(disPos);
        float rayDist = length(disPos);
        float bhDist = min(length(bbPos), 500.0);

        vec3 bhDir = normalize(bbPos);
        float lookingTowardBH = dot(rayDir, bhDir);

        float t_closest = dot(bbPos, rayDir);
        t_closest = clamp(t_closest, 0.0, rayDist); 
        vec3 closestPoint = t_closest * rayDir;
        float impactParam = length(bbPos - closestPoint) / s;

        float viewFactor = lookingTowardBH * exp(-bhDist / (40.0 * s)); 

        float impactFactor = 1.0 / (1.0 + pow(impactParam, 2.6) / 100.0);

        float G = 0.4;
        float totalLensing = G * viewFactor * impactFactor * (1.0 - smoothstep(300.0, 500.0, impactParam * s));

        vec3 toBlackHole = normalize(bbPos - closestPoint);
        vec3 bentRayDir = normalize(rayDir - totalLensing * toBlackHole);

        disPos = bentRayDir * rayDist;
    }
    
    gl_Position = ProjMat * ModelViewMat * vec4(disPos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color) * texelFetch(Sampler2, UV2 / 16, 0);
    overlayColor = texelFetch(Sampler1, UV1, 0);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
#version 150

#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;

uniform mat4 ModelViewMat;

uniform vec3 epicenter;
uniform vec2 state;
    
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform int FogShape;

out float vertexDistance;
out vec4 vertexColor;

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
    disPos.y += (displacement * 60.0 * (1.0 - t) * state.y) * (1.0 - smoothstep(2.0, 4.0, state.x));


    float s = 8.0;
    float s2 = 10.0;
    vec3 bbPos = epicenter + vec3(0.0, 20.0 * s2, 0.0);
    
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

        float impactFactor = 1.0 / (1.0 + pow(impactParam, 2.4) / 100.0);

        float G = 0.28;
        float totalLensing = G * viewFactor * impactFactor * (1.0 - smoothstep(100.0, 300.0, impactParam * s));

        vec3 toBlackHole = normalize(bbPos - closestPoint);
        vec3 bentRayDir = normalize(rayDir - totalLensing * toBlackHole);

        disPos = bentRayDir * rayDist;
    }
    
    gl_Position = ProjMat * ModelViewMat * vec4(disPos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vertexColor = Color;
}
#version 150

#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec3 Normal;

uniform mat4 ModelViewMat;

uniform vec3 epicenter;
uniform vec2 state;
    
uniform mat4 ProjMat;
uniform float LineWidth;
uniform vec2 ScreenSize;
uniform int FogShape;

out float vertexDistance;
out vec4 vertexColor;

const float VIEW_SHRINK = 1.0 - (1.0 / 256.0);
const mat4 VIEW_SCALE = mat4(
    VIEW_SHRINK, 0.0, 0.0, 0.0,
    0.0, VIEW_SHRINK, 0.0, 0.0,
    0.0, 0.0, VIEW_SHRINK, 0.0,
    0.0, 0.0, 0.0, 1.0
);

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
    disPos.y += displacement * 30.0 * (1.0 - t) * state.y;

    vec3 bbPos = epicenter + vec3(0.0, 13.0, 0.0);

    if (state.y != 0.0) {
        // Camera at origin
        vec3 rayDir = normalize(disPos);
        float rayDist = length(disPos);
        float bhDist = length(bbPos);

        // Factor 1: How much are we looking toward the black hole?
        // dot(rayDir, normalize(bbPos)) gives 1 when looking directly at it, 0 when perpendicular
        vec3 bhDir = normalize(bbPos);
        float lookingTowardBH = dot(rayDir, bhDir);

        // Factor 2: Closest point on line segment from camera (0,0,0) to vertex (disPos)
        float t_closest = dot(bbPos, rayDir);
        t_closest = clamp(t_closest, 0.0, rayDist); // Keep on segment
        vec3 closestPoint = t_closest * rayDir;
        float impactParam = length(bbPos - closestPoint);

        // Combine both factors with smooth falloffs
        // Looking toward BH: use distance from camera to BH
        float viewFactor = lookingTowardBH * exp(-bhDist / 30.0); // Exponential falloff

        // Impact parameter: inverse square with smooth rolloff
        float impactFactor = 1.0 / (1.0 + impactParam * impactParam / 100.0);

        // Combined gravitational strength
        float G = 0.4; // Overall strength multiplier
        float totalLensing = G * viewFactor * impactFactor;

        vec3 toBlackHole = normalize(bbPos - closestPoint);
        vec3 bentRayDir = normalize(rayDir - totalLensing * toBlackHole); // Changed + to -

        disPos = bentRayDir * rayDist;
    }
    
    vec4 linePosStart = ProjMat * VIEW_SCALE * ModelViewMat * vec4(disPos, 1.0);
    vec4 linePosEnd = ProjMat * VIEW_SCALE * ModelViewMat * vec4(Position + Normal, 1.0);

    vec3 ndc1 = linePosStart.xyz / linePosStart.w;
    vec3 ndc2 = linePosEnd.xyz / linePosEnd.w;

    vec2 lineScreenDirection = normalize((ndc2.xy - ndc1.xy) * ScreenSize);
    vec2 lineOffset = vec2(-lineScreenDirection.y, lineScreenDirection.x) * LineWidth / ScreenSize;

    if (lineOffset.x < 0.0) {
        lineOffset *= -1.0;
    }

    if (gl_VertexID % 2 == 0) {
        gl_Position = vec4((ndc1 + vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);
    } else {
        gl_Position = vec4((ndc1 - vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);
    }

    vertexDistance = fog_distance(ModelViewMat, Position, FogShape);
    vertexColor = Color;
}
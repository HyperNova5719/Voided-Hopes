#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;

uniform vec3 epicenter;
uniform vec2 state;
    

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform int FogShape;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord1;
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
    disPos.y += (displacement * 30.0 * (1.0 - t) * state.y) / (1.0 + (state.x * 0.1));

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
        float impactFactor = 1.0 / (1.0 + pow(impactParam, 2.5) / 100.0);

        // Combined gravitational strength
        float G = 0.5; // Overall strength multiplier
        float totalLensing = G * viewFactor * impactFactor;

        vec3 toBlackHole = normalize(bbPos - closestPoint);
        vec3 bentRayDir = normalize(rayDir - totalLensing * toBlackHole); // Changed + to -

        disPos = bentRayDir * rayDist;
    }
    
    gl_Position = ProjMat * ModelViewMat * vec4(disPos, 1.0);

    vertexDistance = fog_distance(ModelViewMat, Position, FogShape);
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color) * texelFetch(Sampler2, UV2 / 16, 0);
    texCoord0 = UV0;
    texCoord1 = UV1;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
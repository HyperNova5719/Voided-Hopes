#version 150

in vec3 Position;
in vec2 UV;
in vec4 Color;

uniform mat4 ModelViewMat;

uniform vec3 epicenter;
uniform vec2 state;
    
uniform mat4 ProjMat;

out vec2 texCoord;
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
    disPos.y += displacement * 30.0 * (1.0 - t) * state.y;
    
    gl_Position = ProjMat * ModelViewMat * vec4(disPos, 1.0);

    texCoord = UV;
    vertexColor = Color;
}
from pathlib import Path
import re

def inject_uniform_to_vsh(shader_dir: Path, target_dir: Path, uniform_line: str, inserted_line: str, vec4_replacement: str):
    vsh_files = list(shader_dir.glob('*.vsh'))

    if not vsh_files:
        print("No .vsh files found!")
        return

    print(f"Processing {len(vsh_files)} .vsh files...")
    print()

    updated_count = 0

    target_dir.mkdir(parents=True, exist_ok=True)

    for vsh_file in vsh_files:
        try:
            with open(vsh_file, 'r', encoding='utf-8') as f:
                content = f.read()

            if uniform_line.strip() in content:
                print(f"  Skipped {vsh_file.name}: uniform already exists")
                continue

            # Insert uniforms
            lines = content.splitlines()
            inserted = False
            for i, line in enumerate(lines):
                if line.strip().startswith("uniform "):
                    lines.insert(i + 1, uniform_line)
                    inserted = True
                    break
            if not inserted:
                lines.insert(0, uniform_line)

            # Re-join for searching
            content = "\n".join(lines)

            # Look for vec4(...)
            pattern = r'vec4\s*\('
            match = re.search(pattern, content)

            if match:
                start_pos = match.end()
                paren_count = 1
                end_pos = start_pos

                for i in range(start_pos, len(content)):
                    if content[i] == '(':
                        paren_count += 1
                    elif content[i] == ')':
                        paren_count -= 1
                        if paren_count == 0:
                            end_pos = i
                            break

                if paren_count == 0:
                    original_vec4 = content[match.start():end_pos + 1]
                    pos_line = inserted_line.replace("X", original_vec4)

                    # Work line-by-line
                    lines = content.splitlines()
                    for idx, line in enumerate(lines):
                        if "vec4(" in line:
                            lines.insert(idx, pos_line)
                            lines[idx + 1] = line.replace(original_vec4, vec4_replacement)
                            break

                    new_content = "\n".join(lines)

                    target_file = target_dir / vsh_file.name
                    with open(target_file, 'w', encoding='utf-8') as f:
                        f.write(new_content)

                    print(f"  ✓ Updated {vsh_file.name} -> {target_file}")
                    updated_count += 1
                else:
                    print(f"  Warning: Could not find matching parenthesis in {vsh_file.name}")
            else:
                print(f"  Warning: Could not find vec4() in {vsh_file.name}")

        except Exception as e:
            print(f"  Error processing {vsh_file.name}: {e}")

    print()
    print("=" * 50)
    print(f"Successfully updated {updated_count} .vsh files in {target_dir}")


def main():
    script_dir = Path(__file__).parent.resolve()
    shader_dir = script_dir.parent / "base"
    target_dir = script_dir

    inserted_line = """
    vec3 p = (X).xyz;
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
    """

    vec4_replacement = "vec4(disPos, 1.0)"

    uniform_line = """
uniform vec3 epicenter;
uniform vec2 state;
    """

    print("Shader Uniform Injector")
    print("=" * 50)
    print(f"Shader directory: {shader_dir}")
    print(f"Target directory: {target_dir}")
    print(f"Adding uniform:\n{uniform_line}")
    print("Replacing first vec4() with pos wrapper logic")
    print()

    vsh_files = list(shader_dir.glob('*.vsh'))
    if not vsh_files:
        print(f"Error: No .vsh files found in: {shader_dir}")
        return

    inject_uniform_to_vsh(shader_dir, target_dir, uniform_line, inserted_line, vec4_replacement)

    print("\nComplete!")


if __name__ == "__main__":
    main()

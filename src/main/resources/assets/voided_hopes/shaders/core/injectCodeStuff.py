from pathlib import Path
import re

def inject_uniform_to_vsh(shader_dir: Path, uniform_line: str, vec4_wrapper: str):
    vsh_files = list(shader_dir.glob('*.vsh'))

    if not vsh_files:
        print("No .vsh files found!")
        return

    print(f"Processing {len(vsh_files)} .vsh files...")
    print()

    updated_count = 0

    for vsh_file in vsh_files:
        try:
            with open(vsh_file, 'r', encoding='utf-8') as f:
                content = f.read()

            if uniform_line.strip() in content:
                print(f"  Skipped {vsh_file.name}: uniform already exists")
                continue

            # Add uniform at the beginning
            content = uniform_line + '\n' + content

            # Find the first vec4(...) with balanced parentheses
            pattern = r'vec4\s*\('
            match = re.search(pattern, content)

            if match:
                # Find the matching closing parenthesis
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
                    # Extract the ENTIRE vec4(...) including vec4 and parentheses
                    original_vec4 = content[match.start():end_pos + 1]

                    # Replace X in the wrapper with the original vec4(...)
                    replacement = vec4_wrapper.replace('X', original_vec4)

                    # Reconstruct the content
                    new_content = (
                            content[:match.start()] +
                            replacement +
                            content[end_pos + 1:]
                    )

                    with open(vsh_file, 'w', encoding='utf-8') as f:
                        f.write(new_content)

                    print(f"  ✓ Updated {vsh_file.name}")
                    print(f"      Original: {original_vec4}")
                    print(f"      New: {replacement}")
                    updated_count += 1
                else:
                    print(f"  Warning: Could not find matching parenthesis in {vsh_file.name}")
            else:
                print(f"  Warning: Could not find vec4() in {vsh_file.name}")

        except Exception as e:
            print(f"  Error processing {vsh_file.name}: {e}")

    print()
    print("=" * 50)
    print(f"Successfully updated {updated_count} .vsh files")

def main():
    script_dir = Path(__file__).parent.resolve()
    shader_dir = script_dir

    uniform_line = "//Meow yay"

    vec4_wrapper = "vec4(X.x, X.y + sin(length(X.xz)), X.zw)"

    print("Shader Uniform Injector")
    print("=" * 50)
    print(f"Working directory: {shader_dir}")
    print(f"Adding to first line: {uniform_line}")
    print(f"Replacing first vec4() with: {vec4_wrapper}")
    print()

    vsh_files = list(shader_dir.glob('*.vsh'))
    if not vsh_files:
        print(f"Error: No .vsh files found in: {shader_dir}")
        return

    inject_uniform_to_vsh(shader_dir, uniform_line, vec4_wrapper)

    print("\nComplete!")

if __name__ == "__main__":
    main()
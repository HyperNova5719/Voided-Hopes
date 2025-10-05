from pathlib import Path

def inject_uniform_to_vsh(shader_dir: Path, uniform_line: str, end_line: str):
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
                continue

            lines = content.split('\n')
            lines.insert(0, uniform_line)
            last_brace_index = -1
            for i in range(len(lines) - 1, -1, -1):
                if '}' in lines[i]:
                    last_brace_index = i
                    break

            if last_brace_index != -1:
                lines.insert(last_brace_index, end_line)
                new_content = '\n'.join(lines)
                with open(vsh_file, 'w', encoding='utf-8') as f:
                    f.write(new_content)

                print(f"  ✓ Updated {vsh_file.name}")
                updated_count += 1
            else:
                print(f"  Warning: Could not find closing brace in {vsh_file.name}")

        except Exception as e:
            print(f"  Error processing {vsh_file.name}: {e}")

    print()
    print("=" * 50)
    print(f"Successfully updated {updated_count} .vsh files")

def main():
    script_dir = Path(__file__).parent.resolve()
    shader_dir = script_dir

    uniform_line = "//Meow yay"

    end_line = "gl_Position = 2.0 * gl_Position;"

    print("Shader Uniform Injector")
    print("=" * 50)
    print(f"Working directory: {shader_dir}")
    print(f"Adding to first line: {uniform_line}")
    print(f"Adding before final }}: {end_line}")
    print()

    vsh_files = list(shader_dir.glob('*.vsh'))
    if not vsh_files:
        print(f"Error: No .vsh files found in: {shader_dir}")
        return

    inject_uniform_to_vsh(shader_dir, uniform_line, end_line)

    print("\nComplete!")

if __name__ == "__main__":
    main()
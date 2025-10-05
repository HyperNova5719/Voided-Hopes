import json
from pathlib import Path
from typing import Set

def get_available_shaders(shader_dir: Path) -> Set[str]:
    """Get a set of all available .vsh shader names (without extension)."""
    return {vsh_file.stem for vsh_file in shader_dir.glob('*.vsh')}

def add_namespace_to_jsons(json_dir: Path, namespace: str = "voided_hopes"):
    """
    Add namespace prefix to vertex shader references in JSON files
    that don't already have a namespace prefix.
    """
    json_files = list(json_dir.glob('*.json'))
    available_shaders = get_available_shaders(json_dir)

    print(f"Found {len(available_shaders)} .vsh shaders")
    print(f"Processing {len(json_files)} JSON files...")
    print()

    updated_count = 0

    for json_file in json_files:
        try:
            with open(json_file, 'r', encoding='utf-8') as f:
                data = json.load(f)

            modified = False

            # Check if vertex property exists
            if "vertex" in data:
                vertex_value = data["vertex"]

                # Check if it already has a namespace (contains ':')
                if ":" not in vertex_value:
                    # No namespace, add it if the shader exists
                    if vertex_value in available_shaders:
                        data["vertex"] = f"{namespace}:{vertex_value}"
                        print(f"  {json_file.name}: '{vertex_value}' -> '{namespace}:{vertex_value}'")
                        modified = True
                    else:
                        print(f"  Warning: {json_file.name} references missing shader: {vertex_value}")
                else:
                    # Already has namespace, check if it's minecraft: and the shader exists locally
                    parts = vertex_value.split(":", 1)
                    if len(parts) == 2:
                        prefix, shader_name = parts
                        if prefix == "minecraft" and shader_name in available_shaders:
                            # Change minecraft: to our namespace
                            data["vertex"] = f"{namespace}:{shader_name}"
                            print(f"  {json_file.name}: '{vertex_value}' -> '{namespace}:{shader_name}'")
                            modified = True

            # Write back if changed
            if modified:
                with open(json_file, 'w', encoding='utf-8') as f:
                    json.dump(data, f, indent=4)
                updated_count += 1

        except json.JSONDecodeError as e:
            print(f"  Error: Could not parse {json_file.name}: {e}")
        except Exception as e:
            print(f"  Error processing {json_file.name}: {e}")

    print()
    print("=" * 50)
    print(f"Updated {updated_count} JSON files")

def main():
    # Run in the same directory as the script
    script_dir = Path(__file__).parent.resolve()
    json_dir = script_dir
    namespace = "voided_hopes"

    print("Shader Namespace Adder")
    print("=" * 50)
    print(f"Working directory: {json_dir}")
    print()

    # Check if there are any JSON files
    json_files = list(json_dir.glob('*.json'))
    if not json_files:
        print(f"Error: No .json files found in: {json_dir}")
        return

    # Check if there are any .vsh files
    vsh_files = list(json_dir.glob('*.vsh'))
    if not vsh_files:
        print(f"Error: No .vsh files found in: {json_dir}")
        return

    add_namespace_to_jsons(json_dir, namespace)

    print("\nComplete!")

if __name__ == "__main__":
    main()
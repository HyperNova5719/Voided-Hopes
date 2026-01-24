import os
import json

folder = "."

for filename in os.listdir(folder):
    if filename.endswith(".json"):
        path = os.path.join(folder, filename)

        with open(path, "r", encoding="utf-8") as f:
            data = json.load(f)

        if "uniforms" not in data:
            data["uniforms"] = []

        if not any(u.get("name") == "epicenter" for u in data["uniforms"]):
            data["uniforms"].append({
                "name": "epicenter",
                "type": "float",
                "count": 3,
                "values": [0.0, 0.0, 0.0]
            })

        if not any(u.get("name") == "state" for u in data["uniforms"]):
            data["uniforms"].append({
                "name": "state",
                "type": "float",
                "count": 2,
                "values": [0.0, 0.0]
            })

        if not any(u.get("name") == "corruptionState" for u in data["uniforms"]):
            data["uniforms"].append({
                "name": "corruptionState",
                "type": "float",
                "count": 2,
                "values": [0.0, 0.0]
            })

    # overwrite file
        with open(path, "w", encoding="utf-8") as f:
            json.dump(data, f, indent=4)

        print(f"Updated {filename}")

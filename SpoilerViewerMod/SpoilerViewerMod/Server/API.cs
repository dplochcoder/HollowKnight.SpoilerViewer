using Newtonsoft.Json;
using System.Collections.Generic;

namespace SpoilerViewerMod.Server.API
{
    public record ItemName
    {
        public readonly string name;

        public ItemName(string name) { this.name = name; }
    }

    public record Item
    {
        public readonly ItemName name;
        public bool isTransition;

        public Item(ItemName name) { this.name = name; }
    }
    public record LocationName
    {
        public readonly string name;

        public LocationName(string name) { this.name = name; }
    }

    public record ItemPlacementData
    {
        public readonly ItemName itemName;
        public bool vanilla;

        public ItemPlacementData(ItemName itemName) { this.itemName = itemName; }
    }

    public record Location
    {
        public readonly LocationName name;
        public bool isShop;
        public bool isTransition;
        public string mapAreaName = "Unknown";
        public string titleAreaName = "Unknown";
        public List<ItemPlacementData> itemPlacementDatum = new();

        [JsonIgnore]
        public string Name => name.name;

        public Location(LocationName name) { this.name = name; }
    }

    public record ItemPlacementIndex
    {
        public readonly int index;

        public ItemPlacementIndex(int index) { this.index = index; }
    }

    public record ObtainIndices
    {
        public readonly LocationName locationName;
        public readonly List<ItemPlacementIndex> itemPlacementIndices = new();

        public ObtainIndices(LocationName locationName, List<ItemPlacementIndex> itemPlacementIndices)
        {
            this.locationName = locationName;
            this.itemPlacementIndices = itemPlacementIndices;
        }
    }

    public enum Logic
    {
        IN_LOGIC,
        IN_PURCHASE_LOGIC,
        OUT_OF_LOGIC
    }

    public record LogicMapEntry
    {
        public ObtainIndices key;
        public Logic value;
    }

    public record LogicMap
    {
        public List<LogicMapEntry> entries = new();
    }

    public record LogicMapDeltaEntry
    {
        public ObtainIndices key;
        public Logic value;
    }

    public record LogicMapDelta
    {
        public List<LogicMapDeltaEntry> entries = new();
    }

    public record LogicMapSequenceEntry
    {
        public ObtainIndices key;
        public LogicMapDelta value;
    }

    public record LogicMapSequence
    {
        public LogicMap baseMap;
        public List<LogicMapSequenceEntry> entryList = new();
    }

    public record RandoContext
    {
        public List<Item> items = new();
        public List<Location> locations = new();
        public LogicMapSequence logic = new();
    }

    public record RandoContextRequest
    {
        public List<ObtainIndices> obtains = new();
    }
}

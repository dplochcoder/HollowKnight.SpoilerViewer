using Newtonsoft.Json;
using System.Collections.Generic;

namespace SpoilerViewerMod.Server.API
{
    public record ItemName
    {
        public string name;

        public ItemName(string name) { this.name = name; }
    }

    public record ItemIndex
    {
        public int index;

        public ItemIndex(int index) { this.index = index; }
    }

    public record Item
    {
        public ItemName name;
        public ItemIndex index;

        public Item(ItemName name, ItemIndex index)
        {
            this.name = name;
            this.index = index;
        }
    }
    public record LocationName
    {
        public string name;

        public LocationName(string name) { this.name = name; }
    }

    public record LocationIndex
    {
        public int index;

        public LocationIndex(int index) { this.index = index; }
    }

    public record ItemPlacementData
    {
        public ItemIndex itemIndex;
        public bool vanilla;

        public ItemPlacementData(ItemIndex itemIndex, bool vanilla)
        {
            this.itemIndex = itemIndex;
            this.vanilla = vanilla;
        }
    }

    public record Location
    {
        public LocationName name;
        public LocationIndex index;
        public bool isShop;
        public bool isTransition;
        public string mapAreaName;
        public string titleAreaName;
        public List<ItemPlacementData> itemPlacementDatum = new();

        [JsonIgnore]
        public string Name => name.name;
    }

    public record ItemPlacementIndex
    {
        public int index;

        public ItemPlacementIndex(int index) { this.index = index; }
    }

    public record ObtainIndices
    {
        public LocationIndex locationIndex;
        public List<ItemPlacementIndex> itemPlacementIndices = new();
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

using System;
using System.Collections;
using System.Collections.Generic;

namespace SpoilerViewerMod.Server
{

    public class IndexedList<K, V> : IEnumerable<V>
    {
        public delegate K GetKey(V value);

        private readonly GetKey keyGetter;
        private readonly List<V> list = new();
        private readonly Dictionary<K, int> dict = new();

        public IndexedList(GetKey keyGetter) { this.keyGetter = keyGetter; }

        private int AddInternal(K key, V elem)
        {
            int ret = list.Count;
            list.Add(elem);
            dict[key] = ret;
            return ret;
        }

        public int Add(V elem)
        {
            var key = keyGetter(elem);
            if (dict.ContainsKey(key)) throw new ArgumentException($"Duplicate name: {key}");

            return AddInternal(key, elem);
        }

        public int AddIfMissing(V elem)
        {
            var key = keyGetter(elem);
            return dict.TryGetValue(key, out var index) ? index : AddInternal(key, elem);
        }

        public int IndexOf(K key) => dict[key];

        public V Get(K key) => list[dict[key]];

        public V Get(int index) => list[index];

        public IEnumerator<V> GetEnumerator() => list.GetEnumerator();

        IEnumerator IEnumerable.GetEnumerator() => list.GetEnumerator();

        public int Count => list.Count;
    }
}

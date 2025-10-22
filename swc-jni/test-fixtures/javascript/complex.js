// Complex JavaScript with classes, async, and arrow functions
class DataProcessor {
  constructor(name) {
    this.name = name;
    this.cache = new Map();
  }

  async fetchData(url) {
    if (this.cache.has(url)) {
      return this.cache.get(url);
    }

    const response = await fetch(url);
    const data = await response.json();
    this.cache.set(url, data);
    return data;
  }

  process(items) {
    return items
      .filter(item => item.active)
      .map(item => ({
        ...item,
        processed: true,
        timestamp: Date.now()
      }))
      .reduce((acc, item) => {
        acc[item.id] = item;
        return acc;
      }, {});
  }
}

const processor = new DataProcessor('main');
export default processor;


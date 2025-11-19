// TypeScript with generics and advanced types
type Result<T, E = Error> = 
  | { success: true; data: T }
  | { success: false; error: E };

interface Repository<T> {
  findById(id: string): Promise<T | null>;
  save(entity: T): Promise<void>;
  delete(id: string): Promise<boolean>;
}

class InMemoryRepository<T extends { id: string }> implements Repository<T> {
  private items = new Map<string, T>();

  async findById(id: string): Promise<T | null> {
    return this.items.get(id) || null;
  }

  async save(entity: T): Promise<void> {
    this.items.set(entity.id, entity);
  }

  async delete(id: string): Promise<boolean> {
    return this.items.delete(id);
  }
}

export { Result, Repository, InMemoryRepository };


// Simple TypeScript with basic types
interface Person {
  name: string;
  age: number;
  email?: string;
}

const greet = (person: Person): string => {
  return `Hello, ${person.name}!`;
};

const user: Person = {
  name: "Alice",
  age: 30
};

console.log(greet(user));


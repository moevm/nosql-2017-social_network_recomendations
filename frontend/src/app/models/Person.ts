export class Person {
  id: number;
  name: string;
  avatar_200: string;
  vkId: string;

  constructor(id: number, name: string, avatar_200: string, vkId: string) {
    this.id = id;
    this.name = name;
    this.avatar_200 = avatar_200;
    this.vkId = vkId;
  }
}

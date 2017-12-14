export class Group {
  id: number;
  name: string;
  photo_200: string;
  vkId: string;

  constructor(id: number, name: string, photo_200: string, vkId: string) {
    this.id = id;
    this.name = name;
    this.photo_200 = photo_200;
    this.vkId = vkId;
  }
}

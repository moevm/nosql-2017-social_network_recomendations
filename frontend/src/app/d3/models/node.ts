export class Node implements d3.SimulationNodeDatum {

  x?: number;
  y?: number;
  fx?: number | null;
  fy?: number | null;

  id: string;
  linkCount = 0;
  image: string;
  normal = () => {
    return Math.sqrt(this.linkCount / 300);
  }

  constructor(id, image) {
    this.id = id;
    this.image = image;
  }

  get r() {
    return 50 * this.normal() + 10;
  }

  get color() {
    if (this.id.includes('id')) {
      return 'rgb(255,255,255)';
    } else {
      return 'rgb(0,0,0)';
    }
  }
}

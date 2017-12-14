import {Component, Input} from '@angular/core';
import {Node} from '../../../d3';

@Component({
  selector: '[nodeVisual]',
  template: `
    <svg:g [attr.transform]="'translate(' + (node.x -node.r) + ',' + (node.y-node.r) + ')'">
      <defs>
        <clipPath [attr.id]="'circleView'+node.id">
          <svg:circle
            class="node"
            [attr.fill]="node.color"
            [attr.cx]="node.r"
            [attr.cy]="node.r"
            [attr.r]="node.r">
          </svg:circle>
        </clipPath>
      </defs>

      <svg:image
        [attr.width]="2*node.r" [attr.height]="2*node.r"
        [attr.href]="node.image"
        [attr.clip-path]="'url(#circleView'+ node.id + ')'"
      >
      </svg:image>
      <svg:circle
        [attr.clip-path]="'url(#circleView'+ node.id + ')'"
        [attr.fill]="'none'"
        [attr.cx]="node.r"
        [attr.cy]="node.r"
        [attr.r]="node.r"
        [attr.stroke]="node.color"
        [attr.width]="2"
      >
        >

      </svg:circle>
      <!--<svg:text-->
      <!--class="node-name"-->
      <!--[attr.font-size]="node.fontSize">-->
      <!--{{node.id}}-->
      <!--</svg:text>-->
    </svg:g>
  `,
  styleUrls: ['./node-visual.component.scss']
})
export class NodeVisualComponent {
  @Input('nodeVisual') node: Node;
}

pragma solidity ^0.8.0;

contract MyContract {
    struct Node {
        uint value;
        uint leftChild;
        uint rightChild;
    }

    Node[] public nodes;
    uint public root;

    constructor(uint _value) {
        root = 0;
        nodes.push(Node({
        value: _value,
        leftChild: 0,
        rightChild: 0
        }));
    }

    function insert(uint _value) public {
        _insert(root, _value);
    }

    function _insert(uint _node, uint _value) internal {
        if (_value <= nodes[_node].value) {
            if (nodes[_node].leftChild == 0) {
                nodes[_node].leftChild = nodes.length;
                nodes.push(Node({
                value: _value,
                leftChild: 0,
                rightChild: 0
                }));
            } else {
                _insert(nodes[_node].leftChild, _value);
            }
        } else {
            if (nodes[_node].rightChild == 0) {
                nodes[_node].rightChild = nodes.length;
                nodes.push(Node({
                value: _value,
                leftChild: 0,
                rightChild: 0
                }));
            } else {
                _insert(nodes[_node].rightChild, _value);
            }
        }
    }

    function search(uint _value) public view returns (bool) {
        return _search(root, _value);
    }

    function _search(uint _node, uint _value) internal view returns (bool) {
        if (_node == 0) {
            return false;
        }
        if (_value == nodes[_node].value) {
            return true;
        } else if (_value < nodes[_node].value) {
            return _search(nodes[_node].leftChild, _value);
        } else {
            return _search(nodes[_node].rightChild, _value);
        }
    }

    function getRoot() public view returns (uint) {
        return root;
    }

    function getNode(uint _index) public view returns (uint, uint, uint) {
        return (nodes[_index].value, nodes[_index].leftChild, nodes[_index].rightChild);
    }
}

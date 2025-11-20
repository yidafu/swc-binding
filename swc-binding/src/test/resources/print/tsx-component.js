interface Props {
    name: string;
}
const Component: React.FC<Props> = ({ name })=>{
    return <div>Hello, {name}!</div>;
};

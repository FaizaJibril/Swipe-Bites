import LoginButton from './LoginButton';
import Panel from '../ui/Panel';
import RegisterButton from './RegisterButton';


function Form({ children }) {
    return (
      <Panel title="Welcome">
        <LoginButton />
        <RegisterButton />
      </Panel>
    );
  }
  export default Form;
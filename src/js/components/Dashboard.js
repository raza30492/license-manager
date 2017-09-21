import React, {Component} from 'react';
import { connect } from 'react-redux';
import { localeData } from '../reducers/localization';
import {initialize} from '../actions/misc';

import Box from 'grommet/components/Box';
import Anchor from 'grommet/components/Anchor';
import Section from 'grommet/components/Section';
import Spinning from 'grommet/components/icons/Spinning';
import Menu from 'grommet/components/Menu';
import Header from 'grommet/components/Header';
import Title from 'grommet/components/Title';
import Search from 'grommet/components/Search';

class Dashboard extends Component {
  constructor () {
    super();
    this.state = {
      initializing: false
    };
    this.localeData = localeData();
  }

  componentWillMount () {
    console.log('componentWillMount');
    if (!this.props.misc.initialized) {
      this.setState({initializing: true});
      this.props.dispatch(initialize());
    }
  }

  componentWillReceiveProps (nextProps) {
    if (!this.props.misc.initialized && nextProps.misc.initialized) {
      this.setState({initializing: false});
    }
  }

  render () {
    const {initializing} = this.state;

    if (initializing) {
      return (
        <Box pad={{vertical: 'large'}}>
          <Box align='center' alignSelf='center' pad={{vertical: 'large'}}>
            <Spinning /> Initializing Application ...
          </Box>
        </Box>
      );
    }
    return (
      <Box>
        <Section direction="column" pad={{vertical: 'large', horizontal:'small'}}>
          <h1>Welcome to Andon System Application</h1>

          <Header >
            <Title>
              Sample Title
            </Title>
            <Box flex={true}
              justify='right'
              direction='row'
              responsive={false}>
              <Menu inline={true}
                direction='row'>
                <Menu label='Label 1' 
                  inline={false}>
                  <Anchor href='#'
                    className='active'>
                    First
                  </Anchor>
                  <Anchor href='#'>
                    Second
                  </Anchor>
                  <Anchor href='#'>
                    Third
                  </Anchor>
                </Menu>
                <Menu label='Label 2' 
                  inline={false}>
                  <Anchor href='#'
                    className='active'>
                    First
                  </Anchor>
                  <Anchor href='#'>
                    Second
                  </Anchor>
                  <Anchor href='#'>
                    Third
                  </Anchor>
                </Menu>
              </Menu>
            </Box>
          </Header>

        </Section>
      </Box>
    );
  }
}

Dashboard.contextTypes = {
  router: React.PropTypes.object.isRequired
};

let select = (store) => {
  return { nav: store.nav, misc: store.misc};
};

export default connect(select)(Dashboard);
